precision mediump float;

uniform mat4 uMVPMatrix;

attribute vec2 aTextureCoord;
attribute vec4 aPosition;

varying vec4 vColor;
varying vec2 vTextureCoord;

void main() {
	vec4 position = aPosition;

	vTextureCoord = aTextureCoord;
	gl_Position = uMVPMatrix * position;
}