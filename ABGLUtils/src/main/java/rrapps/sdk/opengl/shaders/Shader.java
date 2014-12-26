/**
 * 
 */
package rrapps.sdk.opengl.shaders;

/**
 * @author Abhishek Bansal
 *  
 */
public class Shader implements IShader
{
    private IShader.ShaderType _shaderType;
    
    /**
     * stores source code of shader 
     */
    private String _shaderSource = "";
    
    public Shader(IShader.ShaderType type)
    {
        _shaderType = type;
    }

    public IShader.ShaderType getShaderType()
    {
        return _shaderType;
    }

    public void setShaderSource(String _shaderSource)
    {
        this._shaderSource = _shaderSource;
    }

    public void setShaderType(IShader.ShaderType _shaderType)
    {
        this._shaderType = _shaderType;
    }
    
    public int load(String fileName)
    {
        return ShaderUtil.LoadShaderFromFile(_shaderType, fileName);
    }
    
    public int load()
    {
        return ShaderUtil.LoadShaderFromString(_shaderType, _shaderSource);
    }
}
