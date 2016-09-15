package com.example.android.opengl.OpenGL.GLobjects.Shader;

import android.opengl.GLES20;

import com.example.android.opengl.OpenGL.MyGLRenderer;

/**
 * Created by Woess on 15.09.2016.
 */
public class StandardShader {
    public int vertexShader;
    public int fragmentShader;
    public int mPositionHandle;
    public int mColorHandle;
    public int mMVPMatrixHandle;
    public int mLightHandle;
    public int mNormalHandle;
    public int mTextureUniformHandle;
    public int mAlphaHandle;
    public int mMMatrixHandle;
    public int mTexHandle;
    public int mProgram;
    public int mLightStrengthHandle;
    public String vertex_shader =
            "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
                    + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.
                    + "uniform vec4 a_Color;        \n"		// Per-vertex color information we will pass in.
                    + "attribute vec2 vTexture;         \n"
                    + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
                    + "attribute vec3 a_Normal;       \n"		// Per-vertex normal information we will pass in.
                    + "varying vec2 f_texCoord;         \n"
                    + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
                    + "varying vec3 v_Position;       \n"		// This will be passed into the fragment shader.
                    + "varying vec3 v_Normal;         \n"		// This will be passed into the fragment shader.
                    + "void main()                    \n" 	// The entry point for our vertex shader.
                    + "{                              \n"
                    + "   v_Color = a_Color;                                       \n"
                    + "  gl_Position = u_MVPMatrix * a_Position;                            \n"
                    + "  f_texCoord = vTexture;                                              \n"
                    + "  v_Normal = vec3(u_MVMatrix * vec4(normalize(a_Normal), 0.0));      \n"
                    + "  v_Position = vec3(u_MVMatrix * a_Position);             \n"
                    + "}                                                                     \n";



    public String fragment_shader =
            "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                    + "uniform vec3 u_LightPos;       \n"	    // The position of the light in eye space.
                    + "uniform float u_Alpha;       \n"	    // The position of the light in eye space.
                    + "uniform float u_LightStrength;       \n"	    // The position of the light in eye space.

                    // precision in the fragment shader.
                    + "uniform sampler2D s_texture;     \n"
                    + "varying vec2 f_texCoord;         \n"
                    + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                    + "varying vec3 v_Position;		\n"		// Interpolated position for this fragment.
                    + "varying vec3 v_Normal;         \n"		// Interpolated normal for this fragment
                    + "void main()                    \n"		// The entry point for our fragment shader.
                    + "{                              \n"
                    + "   float distance = length(u_LightPos - v_Position);                  \n"
                    + "   vec3 lightVector = normalize(u_LightPos - v_Position);             \n"
                    + "   float diffuse = max(dot(v_Normal, lightVector), 0.1);              \n"
                    + "   diffuse = diffuse * (u_LightStrength / (1.0 + (0.25 * distance * distance)));  \n"
                    + "	  vec4 text = texture2D(s_texture, f_texCoord); \n"
                    //               + "	  vec4 col = v_Color  \n"
                    //             + "   gl_FragColor = vec4(v_Color[0]* diffuse * 10.0,v_Color[1]* diffuse * 10.0,v_Color[2]* diffuse * 10.0, u_Alpha);     \n"
                    + "   gl_FragColor = vec4(v_Color[0]*text.r * diffuse,v_Color[1]*text.g * diffuse ,v_Color[2]*text.b * diffuse, u_Alpha);     \n"		// Pass the color directly through the pipeline.
                    + "}                              \n";

    public void init(){

        // prepare shaders and OpenGL program
        vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertex_shader);
        fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragment_shader);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

        mLightHandle = GLES20.glGetUniformLocation (mProgram, "u_LightPos");
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "s_texture");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mMMatrixHandle = GLES20.glGetUniformLocation (mProgram, "u_MVMatrix");
        mAlphaHandle = GLES20.glGetUniformLocation (mProgram, "u_Alpha");
        mLightStrengthHandle = GLES20.glGetUniformLocation (mProgram, "u_LightStrength");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "a_Color");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mNormalHandle = GLES20.glGetAttribLocation(mProgram,   "a_Normal");
        mTexHandle = GLES20.glGetAttribLocation(mProgram,   "vTexture");

    }
}
