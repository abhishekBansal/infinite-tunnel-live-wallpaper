precision mediump float;

uniform mat4 uMVPMatrix;

attribute vec2 aTextureCoord;

varying vec4 vColor;
varying vec2 vTextureCoord;

void main() {
	vTextureCoord = aTextureCoord;

	vec4 position;
	gl_Position = uMVPMatrix * position;
}