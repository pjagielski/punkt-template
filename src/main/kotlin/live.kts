@file:Suppress("UNUSED_LAMBDA_EXPRESSION")

import pl.pjagielski.punkt.config.TrackConfig
import pl.pjagielski.punkt.jam.LFO
import pl.pjagielski.punkt.melody.*
import pl.pjagielski.punkt.melody.Intervals.minor
import pl.pjagielski.punkt.melody.Intervals.pentatonic
import pl.pjagielski.punkt.pattern.*
import pl.pjagielski.punkt.pattern.Param.CUTOFF

{ config: TrackConfig ->

    config.beatsPerBar = 8
    config.bpm = 105

    val low = 0.2f
    val med = 0.5f
    val high = 0.7f

    patterns(beats = 8) {
        + cycle(0.75, 1.25).sample("bd_haus")
        + repeat(4).sample("claps", at = 1.5)
        + repeat(2).sample("hat_2", at = 0.5, amp = med)
        + repeat(0.5).sample("hh", amp = med)
        + repeat(0.5).sample("hh", amp = high, at = 0.25)

        + listOf(0.5, 0.75, 1.5, 2.75)
            .mapIndexed { i, amp -> Sample(7.0 + (0.25 * i), 0.5, "909cp", amp.toFloat()) }
            .asSequence()

        val arpScale = Scale(F, minor)
        val arpProg = listOf(-9, -8, -7, -7)

        val lfo1 = LFO(1000.0, 2000.0, 3.0)
        val lfo2 = LFO(0.05, 0.2, 8.0)

        + arpScale.low()
            .phrase(
                degrees(arpProg.flatMap { cycle(it, 0, it, it, 1, it, it, 2).take(8).toList() }),
                repeat(0.25)
            )
            .synth("tb303", amp = 0.15f)
            .params("sus" to 0.5, "dec" to 0.75, "start" to 200, "res" to 0.1)
            .lfo(lfo1, "cutoff")
            .lfo(lfo2, "res")

    }

}
