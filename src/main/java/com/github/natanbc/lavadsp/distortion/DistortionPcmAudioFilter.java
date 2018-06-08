package com.github.natanbc.lavadsp.distortion;

import com.github.natanbc.lavadsp.ConverterPcmAudioFilter;
import com.github.natanbc.lavadsp.natives.DistortionConverter;
import com.github.natanbc.lavadsp.natives.DistortionNativeLibLoader;
import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Applies simple math to each sample. Base formula is
 * <pre>
 * {@code
 * sampleSin = sinOffset + sin(sample * sinScale);
 * sampleCos = cosOffset + cos(sample * cosScale);
 * sampleTan = tanOffset + tan(sample * tanScale);
 * sample = max(-1, min(1, offset + scale * (useSin ? sampleSin : 1) * (useCos ? sampleCos : 1) * (useTan ? sampleTan : 1)));
 * }
 * </pre>
 */
public class DistortionPcmAudioFilter extends ConverterPcmAudioFilter<DistortionConverter> {
    public static final int SIN = DistortionConverter.SIN;
    public static final int COS = DistortionConverter.COS;
    public static final int TAN = DistortionConverter.TAN;
    
    private volatile double sinOffset = 0;
    private volatile double sinScale = 1;
    private volatile double cosOffset = 0;
    private volatile double cosScale = 1;
    private volatile double tanOffset = 0;
    private volatile double tanScale = 1;
    private volatile double offset = 0;
    private volatile double scale = 1;
    private final AtomicInteger enabled;

    public DistortionPcmAudioFilter(FloatPcmAudioFilter downstream, int channelCount) {
        super(new DistortionConverter(), downstream, channelCount);
        this.enabled = new AtomicInteger(DistortionNativeLibLoader.allFunctions());
    }

    /**
     * Enables the provided functions.
     *
     * <br>Example: {@code enableFunctions(DistortionPcmAudioFilter.SIN | DistortionPcmAudioFilter.COS)}
     *
     * @param functions Functions to enable. Multiple can be enabled by using a bitwise or.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter enableFunctions(int functions) {
        getConverter().enableFunctions(functions);
        enabled.updateAndGet(v->v | (functions & DistortionNativeLibLoader.allFunctions()));
        return this;
    }

    /**
     * Disables the provided functions.
     *
     * <br>Example: {@code disableFunctions(DistortionPcmAudioFilter.SIN | DistortionPcmAudioFilter.COS)}
     *
     * @param functions Functions to disable. Multiple can be disabled by using a bitwise or.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter disableFunctions(int functions) {
        getConverter().disableFunctions(functions);
        enabled.updateAndGet(v->v & ~functions);
        return this;
    }

    /**
     * Returns whether or not a provided function is enabled.
     *
     * <br>If more than one function is provided, this method will return true if <b>any</b> of them
     * is enabled.
     *
     * @param function Function to check.
     *
     * @return {@code true} if the function is enabled.
     */
    public boolean isEnabled(int function) {
        return (enabled.get() & (function & DistortionNativeLibLoader.allFunctions())) != 0;
    }

    /**
     * Returns whether or not all provided functions are enabled.
     *
     * @param functions Functions to test for.
     *
     * @return {@code true} if all functions are enabled.
     */
    public boolean allEnabled(int functions) {
        return (enabled.get() & (functions & DistortionNativeLibLoader.allFunctions())) == functions;
    }

    /**
     * Returns the sin offset.
     *
     * @return The sin offset.
     */
    public double getSinOffset() {
        return sinOffset;
    }

    /**
     * Sets the sin offset.
     *
     * @param sinOffset New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setSinOffset(double sinOffset) {
        getConverter().setSinOffset(sinOffset);
        this.sinOffset = sinOffset;
        return this;
    }

    /**
     * Returns the sin scale.
     *
     * @return The sin scale.
     */
    public double getSinScale() {
        return sinScale;
    }

    /**
     * Sets the sin scale.
     *
     * @param sinScale New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setSinScale(double sinScale) {
        getConverter().setSinScale(sinScale);
        this.sinScale = sinScale;
        return this;
    }

    /**
     * Returns the cos offset.
     *
     * @return The cos offset.
     */
    public double getCosOffset() {
        return cosOffset;
    }

    /**
     * Sets the cos offset.
     *
     * @param cosOffset New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setCosOffset(double cosOffset) {
        getConverter().setCosOffset(cosOffset);
        this.cosOffset = cosOffset;
        return this;
    }

    /**
     * Returns the cos scale.
     *
     * @return The cos scale.
     */
    public double getCosScale() {
        return cosScale;
    }

    /**
     * Sets the cos scale.
     *
     * @param cosScale New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setCosScale(double cosScale) {
        getConverter().setCosScale(cosScale);
        this.cosScale = cosScale;
        return this;
    }

    /**
     * Returns the tan offset.
     *
     * @return The tan offset.
     */
    public double getTanOffset() {
        return tanOffset;
    }

    /**
     * Sets the tan offset.
     *
     * @param tanOffset New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setTanOffset(double tanOffset) {
        getConverter().setTanOffset(tanOffset);
        this.tanOffset = tanOffset;
        return this;
    }

    /**
     * Returns the tan scale.
     *
     * @return The tan scale.
     */
    public double getTanScale() {
        return tanScale;
    }

    /**
     * Sets the tan scale.
     *
     * @param tanScale New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setTanScale(double tanScale) {
        getConverter().setTanScale(tanScale);
        this.tanScale = tanScale;
        return this;
    }

    /**
     * Returns the offset.
     *
     * @return The offset.
     */
    public double getOffset() {
        return offset;
    }

    /**
     * Sets the offset.
     *
     * @param offset New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setOffset(double offset) {
        getConverter().setOffset(offset);
        this.offset = offset;
        return this;
    }

    /**
     * Returns the scale.
     *
     * @return The scale.
     */
    public double getScale() {
        return scale;
    }

    /**
     * Sets the scale.
     *
     * @param scale New value to set.
     *
     * @return {@code this} for chaining calls.
     */
    public DistortionPcmAudioFilter setScale(double scale) {
        getConverter().setScale(scale);
        this.scale = scale;
        return this;
    }
}