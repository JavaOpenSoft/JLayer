/*
 * 11/19/04  1.0 moved to LGPL.
 *
 * 12/12/99	 0.0.7 Renamed class, additional constructor arguments
 *			 and larger write buffers. mdm@techie.com.
 *
 * 15/02/99  Java Conversion by E.B ,javalayer@javazoom.net
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package javazoom.jl.converter;

import javazoom.jl.decoder.Obuffer;

/**
 * Implements an Obuffer by writing the data to a file in RIFF WAVE format.
 *
 * @since 0.0
 */


public class WaveFileObuffer extends Obuffer {
    private final short[] buffer;
    private final short[] bufferp;
    private final int channels;
    private final WaveFile outWave;
    /**
     * Write the samples to the file (Random Acces).
     */
    short[] myBuffer = new short[2];

    /**
     * Creates a new WareFileObuffer instance.
     *
     * @param number_of_channels The number of channels of audio data this buffer will receive.
     * @param freq               The sample frequency of the samples in the buffer.
     * @param FileName           The filename to write the data to.
     */
    public WaveFileObuffer(int number_of_channels, int freq, String FileName) {
        if (FileName == null)
            throw new NullPointerException("FileName");

        buffer = new short[OBUFFERSIZE];
        bufferp = new short[MAXCHANNELS];
        channels = number_of_channels;

        for (int i = 0; i < number_of_channels; ++i)
            bufferp[i] = (short) i;

        outWave = new WaveFile();

        int rc = outWave.OpenForWrite(FileName, freq, (short) 16, (short) channels);
    }

    /**
     * Takes a 16 Bit PCM sample.
     */
    public void append(int channel, short value) {
        buffer[bufferp[channel]] = value;
        bufferp[channel] += channels;
    }

    public void write_buffer(int val) {

        int k = 0;
        int rc = 0;

        rc = outWave.WriteData(buffer, bufferp[0]);
        // REVIEW: handle RiffFile errors.
        for (int i = 0; i < channels; ++i) bufferp[i] = (short) i;
    }

    public void close() {
        outWave.Close();
    }

    /**
     *
     */
    public void clear_buffer() {
    }

    /**
     *
     */
    public void set_stop_flag() {
    }


}
