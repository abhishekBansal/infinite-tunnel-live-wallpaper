package com.rrapps.infinitetunnel;

import rajawali.materials.shaders.FragmentShader;
import rajawali.util.RawShaderLoader;

public class CustomRawFragmentShader extends FragmentShader {

    public void setViewportHeight(float mViewportHeight) {
        this.mViewportHeight = mViewportHeight;
    }

    public void setViewportWidth(float mViewportWidth) {
        this.mViewportWidth = mViewportWidth;
    }

    private float mViewportHeight;
    private float mViewportWidth;

    private int mResolutionHandle;
	
	public CustomRawFragmentShader()
	{
		super();
		mNeedsBuild = false;
		initialize();
	}
	
	@Override
	public void initialize()
	{
		mShaderString = RawShaderLoader.fetch(R.raw.custom_frag_shader);
	}
	
	@Override
	public void main() {
	}
	
	@Override
	public void setLocations(final int programHandle)
	{
		super.setLocations(programHandle);
        //mResolutionHandle = getUniformLocation(programHandle, "uResolution");
	}
	
	@Override
	public void applyParams() 
	{
		super.applyParams();
		//GLES20.glUniform2f(mResolutionHandle, mViewportWidth, mViewportHeight);
	}
}
