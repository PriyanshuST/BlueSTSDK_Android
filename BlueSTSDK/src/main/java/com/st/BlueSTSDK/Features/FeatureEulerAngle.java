package com.st.BlueSTSDK.Features;

import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Node;
import com.st.BlueSTSDK.Utils.NumberConversion;

/**
 * Feature to transmit the Euler angle
 * https://en.wikipedia.org/wiki/Euler_angles#Tait–Bryan_angles
 */
public class FeatureEulerAngle extends Feature {

    public static final String FEATURE_NAME = "Euler Angle";
    public static final String[] FEATURE_UNIT =  new String[]{"°", "°", "°"};
    public static final String[] FEATURE_DATA_NAME = new String[]{"Yaw", "Pitch", "Roll"};

    public static final float DATA_MAX[] = new float[]{360.0f,180.0f,90.0f};
    public static final float DATA_MIN[] = new float[]{0.0f,-180.0f,-90.0f};

    private static final int YAW_INDEX = 0;
    private static final int PITCH_INDEX = 1;
    private static final int ROLL_INDEX = 2;

    /**
     * create a feature that export the euler angle data with a different name
     * @param node node that will export the data
     */
    public FeatureEulerAngle(Node node) {
        super(FEATURE_NAME, node, new Field[]{
                new Field(FEATURE_DATA_NAME[YAW_INDEX], FEATURE_UNIT[YAW_INDEX], Field.Type.Float,
                        DATA_MAX[YAW_INDEX], DATA_MIN[YAW_INDEX]),
                new Field(FEATURE_DATA_NAME[PITCH_INDEX], FEATURE_UNIT[PITCH_INDEX], Field.Type.Float,
                        DATA_MAX[PITCH_INDEX], DATA_MIN[PITCH_INDEX]),
                new Field(FEATURE_DATA_NAME[ROLL_INDEX], FEATURE_UNIT[ROLL_INDEX], Field.Type.Float,
                        DATA_MAX[ROLL_INDEX], DATA_MIN[ROLL_INDEX]),
        });
    }//FeatureMemsSensorFusion

    /**
     * Get the Yaw component
     *
     * @param sample sample data from the sensor
     * @return quaternion Yaw, or Nan if the array doesn't contain data
     */
    public static float getYaw(Sample sample) {
        if(hasValidIndex(sample,YAW_INDEX))
            return sample.data[YAW_INDEX].floatValue();
        //else
        return Float.NaN;
    }

    /**
     * Get the Pitch component
     *
     * @param sample sample data from the sensor
     * @return quaternion Pitch, or Nan if the array doesn't contain data
     */
    public static float getPitch(Sample sample) {
        if(hasValidIndex(sample,PITCH_INDEX))
            return sample.data[PITCH_INDEX].floatValue();
        //else
        return Float.NaN;
    }

    /**
     * Get the Roll component
     *
     * @param sample sample data from the sensor
     * @return quaternion Roll, or Nan if the array doesn't contain data
     */
    public static float getRoll(Sample sample) {
        if(hasValidIndex(sample,ROLL_INDEX))
            return sample.data[ROLL_INDEX].floatValue();
        //else
        return Float.NaN;
    }

    /**
     *
     * @param data       array where read the Field data
     * @param dataOffset offset where start to read the data
     * @return extracted angle and the number or read bytes (12)
     * @throws IllegalArgumentException if the data array has not enough data
     */
    @Override
    protected ExtractResult extractData(long timestamp, byte[] data, int dataOffset) {
        if (data.length - dataOffset < 12)
            throw new IllegalArgumentException("There are no 12 bytes available to read");

        float yaw = NumberConversion.LittleEndian.bytesToFloat(data, dataOffset);
        float pitch = NumberConversion.LittleEndian.bytesToFloat(data, dataOffset + 4);
        float roll = NumberConversion.LittleEndian.bytesToFloat(data, dataOffset + 8);

        return new ExtractResult(new Sample(timestamp, new Number[]{ yaw,pitch,roll},
                getFieldsDesc()),12);
    }

}
