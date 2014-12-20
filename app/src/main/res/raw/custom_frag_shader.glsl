// Ref: http://adrianboeing.blogspot.in/2011/01/webgl-tunnel-effect-explained.html
//		https://www.shadertoy.com/view/Ms2SWW
precision mediump float;
//uniform float uTime;
//uniform vec2 uResolution;
uniform sampler2D uTunnelTexture;

varying vec2 vTextureCoord;

void main(void)
{
    // clamp pixel posiiton in [-1,1]
    //vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / vec2(480.0, 800.0);
    //vec2 uv;

    // calculate angle of current pixel from origin
    // atan return values are in [-pi/2, pi/2]
    // original tutorial uses function atan(p.y, p.x) which gives a horizontal line
    // in left middle as artefact so i will keep this
    //float a = atan(p.y/p.x);
    
    // distance of point from origin
    //float r = length(p);

    // note that uv are from lower left corner and should be in 0-1
    // r is in range [0, sqrt(2)]
    // a is in range [-pi/2, pi/2] so y will be in range [-1/2, 1/2]
    // 3.1416 = pi
    // note that texture is mapped twice devided by a horizontal line
    // spent hours trying to visualize below two line.. no luck ! :-/ :'(
    //uv.x = .2/r; 
    //uv.y = a/(3.1416);
    
    // add global time for a moving tunnel
    //uv.x = uv.x + uTime/2.0;
    
    // multiplication by r to give a darkened effect  in center
    vec4 col = texture2D(uTunnelTexture, vTextureCoord);//.xyz;// * r;
    
    gl_FragColor = col;//vec4(col,1.0);
}