package com.rrapps.infinitetunnel;

import android.content.Context;
import android.opengl.GLES20;

import junit.framework.Assert;

import rrapps.sdk.opengl.GLUtils;
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
                -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f
        };

        setCoordsPerVertex(3);

        // ask to setup buffers to super class
        _setupVertexBuffer();
    }

    private void _setupShader()
    {
        IShader vertexShader = new Shader(IShader.ShaderType.VERTEX_SHADER);
        //vertexShader.setShaderSource(ShaderLibrary.SimpleTextureVertexShaderCode);
        int vertexShaderHandle = vertexShader.load(R.raw.tunnel_vert, getContext());

        IShader fragmentShader = new Shader(IShader.ShaderType.FRAGMENT_SHADER);
        //fragmentShader.setShaderSource(ShaderLibrary.SimpleTextureFragmentShaderCode);
        int fragmentShaderHandle = fragmentShader.load(R.raw.tunnel_frag, getContext());

        if(vertexShaderHandle <=0)
            Assert.fail("could not compile vertex shader");

        if(fragmentShaderHandle <=0)
            Assert.fail("could not compile fragment shader");

        Program defaultProgram = new Program(vertexShaderHandle, fragmentShaderHandle);
        if(!defaultProgram.linkProgram())
            Assert.fail("Could not link shader program");
        _program = defaultProgram.getID();
    }

    /* (non-Javadoc)
     * @see abhishek.sdk.opengl.geometry.IGeometry#draw(float[])
     */
    @Override
    public void draw(float[] mvpMatrix)
    {
        // Add program to OpenGL environment
        GLES20.glUseProgram(_program);

        // get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(_program, "aPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, _coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);
        GLUtils.checkGlError("glVertexAttribPointer");

        // get handle to shape's transformation matrix
        int mvpMatrixHandle = GLES20.glGetUniformLocation(_program, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        GLUtils.checkGlError("glGetUniformLocation");

        int textureUniformHandle = GLES20.glGetUniformLocation(_program, "uTunnelTexture");
        GLES20.glUniform1i(textureUniformHandle, _textureHandle);
        GLUtils.checkGlError("glGetUniformLocation");

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _textureHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(textureUniformHandle, 0);

        // Draw the tunnel
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
