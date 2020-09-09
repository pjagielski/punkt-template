@file:Suppress("UNUSED_LAMBDA_EXPRESSION")

import pl.pjagielski.punkt.config.TrackConfig
import pl.pjagielski.punkt.fx.*
import pl.pjagielski.punkt.melody.*
import pl.pjagielski.punkt.melody.Intervals.minor
import pl.pjagielski.punkt.param.LFO
import pl.pjagielski.punkt.pattern.*

{ config: TrackConfig ->

    config.tracks[1].reverb(level = 0.75, room = 0.8, mix = 0.5)
    config.tracks[1].delay(level = 0.75, echo = 0.75, echotime = 4.0)
    config.tracks[1].comp(level = 0.65, dist = 0.8)

    config.tracks[2].reverb(level = 0.75, room = 2.0, mix = 0.8)
    config.tracks[2].delay(level = 0.75, echo = 0.75, echotime = 8.0)
    config.tracks[2].comp(level = 0.65, dist = 0.7)

    config.beatsPerBar = 8
    config.bpm = 99

    patterns(beats = 8) {
        + cycle(0.75, 1.25).sample("bd_haus")

        + repeat(4).sample("claps", at = 1.5)
            .hpf(1000)
            .track(1)

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
            .synth("lead")
            .amp(0.2)
            .params("sus" to 0.5, "dec" to 0.75, "res" to 0.1)
            .params("cutoff" to LFO(100, 2000, 16))
            .dist(LFO(0.3, 0.5, 4))
            .chop(config, cycle(1, 2))
            .djf(LFO(0.35, 0.45, 8))
            .track(2)

    }

}
