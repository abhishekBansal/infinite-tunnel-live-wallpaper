/**
 * 
 */
package rrapps.sdk.opengl.shaders;
import rrapps.sdk.utils.LOGUtil;

import java.io.BufferedReader;
import java.io.FileReader;

import android.opengl.GLES20;
import android.util.Log;

/**
 * @author Abhishek Bansal
 *
 */
public final class ShaderUtil
{
    /**
     * 
     * @param shaderType
     * @param source
     * @return shader id
     */
    public static int LoadShaderFromString(IShader.ShaderType shaderType, String source)
    {
        return _compileShader(shaderType.getType(), source);
    }
    
    /**
     * 
     * @param shaderType
     * @param fileName
     * @return id of the shader if compilation was successful 0 otherwise
     */
    public static int LoadShaderFromFile(IShader.ShaderType shaderType, String fileName)
    {
        String source = _getSourceFromFile(fileName);
        return _compileShader(shaderType.getType(), source);
    }
    
    private static int _compileShader(int type, String source)
    {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) 
        {
            Log.e(LOGUtil.LOG_TAG, "Could not compile shader: ");
            Log.e(LOGUtil.LOG_TAG, GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }

        return shader;
    }

    /**
     * following function returns shader as string from a text file on disk
     * @param filename shader file
     * @return
     */
    private static String _getSourceFromFile(String filename)
    {
        StringBuilder source = new StringBuilder();
        String line = null ;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while( (line = reader.readLine()) !=null )
            {
                source.append(line);
                source.append('\n');
            }
            reader.close();
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("unable to load shader from file ["+filename+"]", e);
        }
 
        return source.toString();
    }
}
