package com.rrapps.infinitetunnel;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

public class InfiniteTunnelRenderer extends RajawaliRenderer {

    public InfiniteTunnelRenderer(Context context) {
		super(context);
	}

    Material mMaterial;
	public void initScene() {
		getCurrentCamera().setPosition(0, 0, 2);
		getCurrentCamera().setLookAt(0, 0, 0);

		try {
            float planeWidth = 1.0f;
            float planeHeight = planeWidth * getViewportHeight()/getViewportWidth();
            Plane fullScreenPlane =
                    new Plane(planeWidth, planeHeight, 1, 1, Vector3.Axis.Z);
            fullScreenPlane.setRotY(180);
            CustomRawVertexShader vertexShader = new CustomRawVertexShader();

            CustomRawFragmentShader fragmentShader = new CustomRawFragmentShader();
            fragmentShader.setViewportHeight(getViewportHeight());
            fragmentShader.setViewportWidth(getViewportWidth());

            mMaterial = new Material(vertexShader, fragmentShader);
            mMaterial.addTexture(new Texture("uTunnelTexture", R.drawable.round_brick_tilable));
            mMaterial.enableTime(true);
            fullScreenPlane.setMaterial(mMaterial);

            getCurrentScene().addChild(fullScreenPlane);
		} catch (ATexture.TextureException e) {
			e.printStackTrace();
		}
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

    private float mTime = 0.0f;
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
        mTime += .01f;

        // tunnel gets fucked up in sometime if we don't do this
        // as of now not sure if its a hack or its needed
        // and 2 is just a magic number.. I got it by hit and trial
        if(mTime > 2.0f)
            mTime = 0.0f;

        mMaterial.setTime(mTime);
    }
}
