package com.rrapps.infinitetunnel;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rrapps.sdk.opengl.geometry.ITexturedGeometry;
import rrapps.sdk.opengl.shaders.Program;


/**
 * author: Abhishek Bansal
 */

public class InfiniteTunnelRenderer implements GLWallpaperService.Renderer {

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    /**
     * Tunnel plane
     */
    private ITexturedGeometry mTunnelPlane;

    /**
     * shader program
     */
    Program mTunnelProgram;

    public Context getContext() {
        return mContext;
    }

    Context mContext;
    InfiniteTunnelRenderer(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Set the background clear color to gray.
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 2.0f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = 0.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        // initialize plane
        mTunnelPlane = new TunnelGeometry(getContext());
        mTunnelPlane.setTextureEnabled(true);
        mTunnelPlane.loadTextureFromResource(getContext(), R.drawable.brick_red);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);

        ((TunnelGeometry)mTunnelPlane).setViewportDimensions(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.2f, 0.4f, 0.2f, 1f);

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        mTunnelPlane.draw(mMVPMatrix);
    }

    /**
     * Called when the engine is destroyed. Do any necessary clean up because
     * at this point your renderer instance is now done for.
     */
    public void release() {
    }

}
