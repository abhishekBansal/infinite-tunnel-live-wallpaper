package com.rrapps.infinitetunnel;

import android.content.Context;
import android.util.Log;

import com.rrapps.infinitetunnel.model.Settings;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

/**
 * author: Abhishek Bansal
 */

public class InfiniteTunnelRenderer extends RajawaliRenderer {

    public InfiniteTunnelRenderer(Context context) {
		super(context);
        setFrameRate(30);
	}

    Material mMaterial;
    private final String TunnelTextureName = "uTunnelTexture";
	public void initScene() {

		getCurrentCamera().setPosition(0, 0, 2);
		getCurrentCamera().setLookAt(0, 0, 0);

		try {
            float planeWidth = 1.0f;
            float planeHeight = planeWidth * getViewportHeight()/getViewportWidth();
            Plane fullScreenPlane =
                    new Plane(planeWidth, planeHeight, 1, 1, Vector3.Axis.Z);
            fullScreenPlane.setRotY(180);
            TunnelVertexShader vertexShader = new TunnelVertexShader();

            TunnelFragmentShader fragmentShader = new TunnelFragmentShader();
            fragmentShader.setViewportHeight(getViewportHeight());
            fragmentShader.setViewportWidth(getViewportWidth());

            mMaterial = new Material(vertexShader, fragmentShader);
            mMaterial.addTexture(new Texture(TunnelTextureName, R.drawable.bricks_stone));
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

        // check if some settings have been changed
        if(InfiniteTunnelApplication.SettingsInstance.isSettingsChanged())
            _handleSettingChanged();
        mTime += .01f;

        // tunnel gets fucked up in sometime if we don't do this
        // as of now not sure if its a hack or its needed
        // and 2 is just a magic number.. I got it by hit and trial
        if(mTime > 2.0f)
            mTime = 0.0f;

        mMaterial.setTime(mTime);
    }

    /**
     * so i guess mostly texture is difficult, rest all should be easy
     * also i don't think that reading shared preferences in drawloop per frame is a good idea
     * so i will cache all current setting in ram and re-read it only when there is a change
     *
     * Also I do not think that it is the best way to do it, but nonetheless, this is the only way i know
     * right now and yes its messy
     */
    private void _handleSettingChanged() {
        Settings settings = InfiniteTunnelApplication.SettingsInstance;
        settings.readSettings();

        _handleTextureChange(settings.getCurrentTextureNo());
        // make sure that this flag is unset in end
        settings.setSettingsChanged(false);
    }

    private void _handleTextureChange(int currentTextureNo) {
        // remove existing texture
        for(ATexture texture : mMaterial.getTextureList())
            mMaterial.removeTexture(texture);

        ATexture newTexture;
        int drawableId = 0;
        switch (currentTextureNo) {
            case 1:
                drawableId = R.drawable.brick_red;
                break;
            case 2:
                drawableId = R.drawable.bricks_stone;
                break;
            case 3:
                drawableId = R.drawable.nebula3;
                break;
            case 4:
                drawableId = R.drawable.round_brick_tilable;
                break;
            default:
                drawableId = R.drawable.bricks_stone;
                break;
        }

        newTexture = new Texture(TunnelTextureName, drawableId);
        try {
            mMaterial.addTexture(newTexture);
        } catch (ATexture.TextureException e) {
            Log.e(InfiniteTunnelApplication.LogTag, "Couldn't change texture " + currentTextureNo);
            e.printStackTrace();
        }
    }


}
