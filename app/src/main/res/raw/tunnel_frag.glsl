// Ref: http://adrianboeing.blogspot.in/2011/01/webgl-tunnel-effect-explained.html
//      https://www.shadertoy.com/view/Ms2SWW
#ifdef GL_FRAGMENT_PRECISION_HIGH
    precision highp float;
#else
    precision mediump float;
#endif

uniform float uTime;
uniform float uSpeed;
uniform float uBrightness;
uniform vec2 uResolution;
uniform sampler2D uTunnelTexture;

// square realted variables
uniform int uIsSquare;

// accelerometer input
uniform float uDeviationX;
uniform float uDeviationY;

// center bright or dark
uniform int uIsCenterBright;

void main(void)
{
    //float speed = 1.0;
    float scaledTime = uTime * uSpeed;
    // clamp pixel posiiton in [-1,1]
    vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / uResolution.xy;

    // this is to fix aspect ratio of tunnel center
    p.y = p.y * uResolution.y/uResolution.x;

    // apply accelerometer
    p.x = p.x + clamp(uDeviationX, -0.7, 0.7);

    vec2 uv;

    // calculate angle of current pixel from origin
    // atan return values are in [-pi/2, pi/2]
    // original tutorial uses function atan(p.y, p.x) which gives a horizontal line
    // in left middle as artefact so i will keep this
    float a = atan(p.y, p.x);
    
    // distance of point from origin
    float r;
    
    float power = 4.0;
    if(uIsSquare == 0)
        r = length(p); // circle
    else
        // http://en.wikipedia.org/wiki/Minkowski_distance
        r = pow( pow(p.x*p.x,power) + pow(p.y*p.y,power), 1.0/(2.0*power) );

    // note that uv are from lower left corner and should be in 0-1
    // r is in range [0, sqrt(2)]
    // a is in range [-pi/2, pi/2] so y will be in range [-1/2, 1/2]
    // 3.1416 = pi
    // note that texture is mapped twice devided by a horizontal line
    // spent hours trying to visualize below two line.. no luck ! :-/ :'(
    uv.x = .2/r; 
    uv.y = a/(3.1416);
    
    // add global time for a moving tunnel
    uv.x = uv.x + scaledTime;
    
    if(uIsSquare == 1)
        r = smoothstep(0.1, 1.0, r);

    float brightness = uBrightness;
    if(uIsCenterBright == 1)
    {
        r = 1.0 - r;
        // pump up the brightness for better effect
        brightness = brightness * 2.0;
    }

    // multiplication by r to give a darkened effect  in center
    vec3 col = texture2D(uTunnelTexture, uv).xyz * r;
    
    gl_FragColor = vec4(col,1.0) * brightness;
}