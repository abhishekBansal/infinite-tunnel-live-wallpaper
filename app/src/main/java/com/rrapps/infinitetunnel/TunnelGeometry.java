package com.rrapps.infinitetunnel;

import android.content.Context;
import android.opengl.GLES20;

import com.rrapps.infinitetunnel.model.Settings;

import junit.framework.Assert;

import rrapps.sdk.opengl.geometry.AbstractGeometry;
import rrapps.sdk.opengl.shaders.IShader;
import rrapps.sdk.opengl.shaders.Program;
import rrapps.sdk.opengl.shaders.Shader;

/**
 * @author Abhishek Bansal
 *
 */
public class TunnelGeometry extends AbstractGeometry
{

    /**
     * position attribute handle
     */
    private int mPositionHandle;

    /**
     * uniform var handles
     */
    private int mMVPMatrixHandle;
    private int mTextureUniformHandle;
    private int mTimeUniformHandle;
    private int mSpeedUniformHandle;
    private int mResolutionUniformHandle;
    private int mBrightnessUniformHandle;

    private int mIsSquareUniformHandle;
    private int mIsBrightCenterUniformHandle;

    private int mXDeviationUniformHandle;
    private int mYDeviationUniformHandle;

    private float mTime = 0.0f;
    private float mViewportWidth = 480.0f;
    private float mViewportHeight = 800.0f;
    private float mXDeviation = 0.0f;
    private float mYDeviations = 0.0f;


    /**
     * @param vertices
     */
    public TunnelGeometry(float[] vertices)
    {
        super(vertices);
    }

    public Context getContext() {
        return mContext;
    }

    private Context mContext;
    public TunnelGeometry(final Context context)
    {
        mContext = context;
        _setupShader();
        _vertices = new float [] {
                -1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                1.0f, 1.0f, 0.0f
        };

        setCoordsPerVertex(3);

        // ask to setup buffers to super class
        _setupVertexBuffer();
    }

    private void _setupShader()
    {
        IShader vertexShader = new Shader(IShader.ShaderType.VERTEX_SHADER);
        int vertexShaderHandle = vertexShader.load(R.raw.tunnel_vert, getContext());

        IShader fragmentShader = new Shader(IShader.ShaderType.FRAGMENT_SHADER);
        int fragmentShaderHandle = fragmentShader.load(R.raw.tunnel_frag, getContext());

        if(vertexShaderHandle <=0)
            Assert.fail("could not compile vertex shader");

        if(fragmentShaderHandle <=0)
            Assert.fail("could not compile fragment shader");

        Program defaultProgram = new Program(vertexShaderHandle, fragmentShaderHandle);
        if(!defaultProgram.linkProgram())
            Assert.fail("Could not link shader program");
        _program = defaultProgram.getID();

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(_program, "aPosition");
        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(_program, "uMVPMatrix");

        mTextureUniformHandle = GLES20.glGetUniformLocation(_program, "uTunnelTexture");

        mTimeUniformHandle = GLES20.glGetUniformLocation(_program, "uTime");

        mSpeedUniformHandle = GLES20.glGetUniformLocation(_program, "uSpeed");

        mResolutionUniformHandle = GLES20.glGetUniformLocation(_program, "uResolution");

        mBrightnessUniformHandle = GLES20.glGetUniformLocation(_program, "uBrightness");

        mIsSquareUniformHandle = GLES20.glGetUniformLocation(_program, "uIsSquare");

        mIsBrightCenterUniformHandle = GLES20.glGetUniformLocation(_program, "uIsCenterBright");

        //mXDeviationUniformHandle = GLES20.glGetUniformLocation(_program, "uDeviationX");
        //mYDeviationUniformHandle = GLES20.glGetUniformLocation(_program, "uDeviationY");
    }

    /* (non-Javadoc)
     * @see abhishek.sdk.opengl.geometry.IGeometry#draw(float[])
     */
    @Override
    public void draw(float[] mvpMatrix)
    {
        // Add program to OpenGL environment
        GLES20.glUseProgram(_program);
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, _coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Setup Texture
        GLES20.glUniform1i(mTextureUniformHandle, _textureHandle);
        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _textureHandle);
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        // update resolution
        GLES20.glUniform2f(mResolutionUniformHandle, mViewportWidth, mViewportHeight);

        // update time
        GLES20.glUniform1f(mTimeUniformHandle, mTime);

        // update speed
        float speed = Settings.getInstance(mContext).getSpeed();
        GLES20.glUniform1f(mSpeedUniformHandle, speed);

        // if time if greater then 1 then reset
        // otherwise tunnel gets fucked up
        // since we are multiplying in shader devide it here to compensate
        // so that uv.x is wrapped everytime scaledTime(in shader) reaches 1
        mTime += 0.01f;
        if(mTime > 1.0f/speed)
            mTime = 0.0f;

        // update brightness
        GLES20.glUniform1f(mBrightnessUniformHandle, Settings.getInstance(mContext).getBrightness());

        // see if its a square tunnel
        boolean isSquare = Settings.getInstance(mContext).isSquareShapedTunnel();
        if(isSquare)
            GLES20.glUniform1i(mIsSquareUniformHandle, 1);
        else
            GLES20.glUniform1i(mIsSquareUniformHandle, 0);

        boolean isBrightCenter = Settings.getInstance(mContext).isCenterBright();
        if(isBrightCenter)
            GLES20.glUniform1i(mIsBrightCenterUniformHandle, 1);
        else
            GLES20.glUniform1i(mIsBrightCenterUniformHandle, 0);

        // set camera offsets
        // GLES20.glUniform1f(mXDeviationUniformHandle, mXDeviation);

        // Draw the tunnel
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void setViewportDimensions(int width, int height) {
        mViewportWidth = width;
        mViewportHeight = height;
    }

    public void setCameraDeviations(float xDeviation, float yDeviation) {
        mXDeviation = xDeviation;
        mYDeviations = yDeviation;
    }
}
