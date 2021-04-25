@file:Suppress("UNUSED_LAMBDA_EXPRESSION")

import pl.pjagielski.punkt.config.TrackConfig
import pl.pjagielski.punkt.fx.*
import pl.pjagielski.punkt.jam.GlobalFX
import pl.pjagielski.punkt.melody.*
import pl.pjagielski.punkt.melody.Intervals.minor
import pl.pjagielski.punkt.pattern.*

{ config: TrackConfig ->

    val scale = Scale(A, minor)

    config.tracks[1].reverb(level = 0.75, room = 0.8, mix = 0.5)
    config.tracks[1].delay(level = 0.0, echo = 0.75, echotime = 2.0)

    config.tracks[2].reverb(level = 0.75, room = 2.0, mix = 0.8)
    config.tracks[2].delay(level = 0.75, echo = 0.75, echotime = 4.0)

    val beats = 8

    config.beatsPerBar = beats
    config.bpm = 92

    patterns(beats = beats) {

        val (Am, Emsus4, Em) = listOf(
            Chord.I.inversion(1), // -> Am
            Chord.V.sus4().inversion(2).low(), // -> Emsus4
            Chord.V.inversion(2).low() // -> Em
        )

        + scale.high()
            .phrase(
                chords(
                    listOf((Am to 8), (Emsus4 to 3), (Em to 5))
                        .flatMap { (ch, i) -> (1..i).map { ch } }
                ),
                repeat(0.5)
            )
            .every(3, { step -> step.copy(beat = step.beat + 0.04) }, from = 1)
            .every(3, { step -> step.copy(beat = step.beat + 0.09) }, from = 2)
            .synth("piano")
            .waveDist(0.3)
            .track(1)
            .amp(0.5)
            .djf(0.75)
//            .mute()

        val (a, b, e) = listOf(0, 1, -3)
        fun Scale.bass() =
            this.low().low().phrase(
                // -> A, B -> E, E ->
                degrees(listOf(a, null, b, null, e, null, e)),
                cycle(1.5, 1.5, 0.75, 0.25)
            )

        + scale
            .bass()
            .synth("tr808")
            .amp(1.25)
            .track(1)
            .djf(0.4)
//            .mute()

        + scale
            .bass()
            .synth("piano")
            .amp(0.65)
//            .mute()

        + cycle(1.25, 0.75, 2.0).sample("bd_haus")
            .amp(1.25)
//            .mute()

        + repeat(2.0).sample("sd", at = 1.0)
            .amp(0.85)
//            .mute()

        + repeat(2.0).sample("snare_3", at = 1.0)
            .amp(0.3)
            .chop(config, 2)
//            .mute()

        + repeat(2.0).sample("hho", at = 0.5)
            .amp(1.25)
//            .mute()

    }

}
