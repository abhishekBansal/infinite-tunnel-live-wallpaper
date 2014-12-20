package com.rrapps.infinitetunnel;

import android.opengl.GLES20;

import rajawali.materials.shaders.FragmentShader;
import rajawali.util.RawShaderLoader;

public class TunnelFragmentShader extends FragmentShader {

    public void setViewportHeight(float mViewportHeight) {
        this.mViewportHeight = mViewportHeight;
    }

    public void setViewportWidth(float mViewportWidth) {
        this.mViewportWidth = mViewportWidth;
    }

    private float mViewportHeight;
    private float mViewportWidth;

    private int mResolutionHandle;
	
	public TunnelFragmentShader()
	{
		super();
		mNeedsBuild = false;
		initialize();
	}
	
	@Override
	public void initialize()
	{
		mShaderString = RawShaderLoader.fetch(R.raw.tunnel_frag);
	}
	
	@Override
	public void main() {
	}
	
	@Override
	public void setLocations(final int programHandle)
	{
		super.setLocations(programHandle);
        mResolutionHandle = getUniformLocation(programHandle, "uResolution");
	}
	
	@Override
	public void applyParams() 
	{
		super.applyParams();
		GLES20.glUniform2f(mResolutionHandle, mViewportWidth, mViewportHeight);
	}
}
