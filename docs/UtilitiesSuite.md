# UtilitiesSuite

UtilitiesSuite is the main file of this mod. It is what brings all the files together.

- **Source:** [UtilitiesSuite.java](/src/utilities/UtilitiesSuite.java)
- **Extends / Implements:** extends Mod from mindustry

## Responsibility

- **Owns the feature lifecycle** — constructs features, registers listeners, drives the update loop.
- **Platform split** — picks `DesktopUi` or `MobileUi` based on `Vars.mobile`.
- **HUD mounting** — attaches the UI on world load, detaches it on return to menu.
- **Throttling** — runs the update loop twice per second, not every frame.

## API

- [public UtilitiesSuite()](/src/utilities/UtilitiesSuite.java#L30,12)
  
  - UtilitiesSuite() is a constructor, it's job is to:
    - initialize Features and UI
    - **registers** Mod loaded lister, world loaded listener, tick register, and game state change listener

## Notes

- [firstTick](/src/utilities/UtilitiesSuite.java#L28,21)
  - i put that there because when the world first loads, there would be a 2 second wait before PowerGrid.java gets triggered to update
