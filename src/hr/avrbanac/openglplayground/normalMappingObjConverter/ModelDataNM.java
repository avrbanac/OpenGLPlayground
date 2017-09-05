package hr.avrbanac.openglplayground.normalMappingObjConverter;

/**
 *
 * @author avrbanac
 * @version 1.0.18
 */
public class ModelDataNM {

	private final float[] vertices;
	private final float[] textureCoords;
	private final float[] normals;
	private final float[] tangents;
	private final int[] indices;
	private final float furthestPoint;

	public ModelDataNM(float[] vertices, float[] textureCoords, float[] normals, float[] tangents, int[] indices,
			float furthestPoint) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.furthestPoint = furthestPoint;
		this.tangents = tangents;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}
	
	public float[] getTangents(){
		return tangents;
	}

	public float[] getNormals() {
		return normals;
	}

	public int[] getIndices() {
		return indices;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}

}
