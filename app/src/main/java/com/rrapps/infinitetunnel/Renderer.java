package com.rrapps.infinitetunnel;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.renderer.RajawaliRenderer;

public class Renderer extends RajawaliRenderer {

    public Renderer(Context context) {
		super(context);
	}

	public void initScene() {
		getCurrentCamera().setPosition(0, 0, 4);
		getCurrentCamera().setLookAt(0, 0, 0);

		try {
            rajawali.primitives.Plane fullScreenPlane = new rajawali.primitives.Plane(1.0f, 1.0f, 1, 1, Vector3.Axis.Z);
            fullScreenPlane.setRotY(180);
            CustomRawVertexShader vertexShader = new CustomRawVertexShader();

            CustomRawFragmentShader fragmentShader = new CustomRawFragmentShader();
            fragmentShader.setViewportHeight(getViewportHeight());
            fragmentShader.setViewportWidth(getViewportWidth());

            Material material = new Material(vertexShader, fragmentShader);
            material.addTexture(new Texture("uTunnelTexture", R.drawable.round_brick_tilable));
            //material.enableTime(true);
            fullScreenPlane.setMaterial(material);

            getCurrentScene().addChild(fullScreenPlane);
		} catch (ATexture.TextureException e) {
			e.printStackTrace();
		}
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
