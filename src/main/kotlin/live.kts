@file:Suppress("UNUSED_LAMBDA_EXPRESSION")

import pl.pjagielski.punkt.config.TrackConfig
import pl.pjagielski.punkt.jam.LFO
import pl.pjagielski.punkt.melody.*
import pl.pjagielski.punkt.melody.Intervals.minor
import pl.pjagielski.punkt.pattern.*

{ config: TrackConfig ->

    config.beatsPerBar = 8
    config.bpm = 99

    val low = 0.2f
    val med = 0.5f
    val high = 0.7f

    patterns(beats = 8) {
        + cycle(0.75, 1.25).sample("bd_haus")
         + repeat(4).sample("claps", at = 1.5)
            .fx("delay", "decaytime" to 0.75, "bpm" to config.bpm / 2)
            .fx("delay", "echo" to 0.75, "bpm" to config.bpm)
            .fx("hpf", "cutoff" to 1000)

        + listOf(0.5, 0.75, 1.5, 2.75)
            .mapIndexed { i, amp -> Sample(7.0 + (0.25 * i), 0.5, "909cp", amp.toFloat()) }
            .asSequence()

        val arpScale = Scale(F, minor)
        val arpProg = listOf(-9, -8, -7, -7)

        + arpScale.low()
            .phrase(
                degrees(arpProg.flatMap { cycle(it, 0, it, it, 1, it, it, 2).take(8).toList() }),
                repeat(0.25)
            )
            .synth("lead", amp = 0.25f)
            .params("sus" to 0.5, "dec" to 0.75, "res" to 0.1)
            .params("cutoff" to LFO(100.0, 2000.0, 16.0))
            .params("start" to LFO(100, 500, 8))
            .fx("dist", "drive" to LFO(0.1, 0.3, 4))
            .fx("delay", "bpm" to config.bpm, "echotime" to config.secsPerBeat)
            .fx("chop", "chop" to 2, "sus" to config.secsPerBeat)
            .fx("hpf", "cutoff" to 100)

    }

}
