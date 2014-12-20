package com.rrapps.infinitetunnel;


import rajawali.materials.shaders.VertexShader;
import rajawali.util.RawShaderLoader;

public class TunnelVertexShader extends VertexShader {
	public TunnelVertexShader()
	{
		super();
		mNeedsBuild = false;
		initialize();
	}
	
	@Override
	public void initialize()
	{
		mShaderString = RawShaderLoader.fetch(R.raw.tunnel_vert);
	}
	
	@Override
	public void main() {

	}
	
	@Override
	public void setLocations(final int programHandle)
	{
		super.setLocations(programHandle);
	}
	
	@Override
	public void applyParams() 
	{
		super.applyParams();
	}
}
