import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

const weaponsAndShieldsGame = {
  ...fooGame,
  player: {
    ...fooGame.player,
    shield: { name: "foo-shield" },
    weapon: { name: "foo-weapon" },
  },
}

let wrapper
let dispatch
let getState
let spyMiddleware
beforeEach(() => {
  spyMiddleware = makeSpyMiddleware()
  const App = makeApp(undefined, spyMiddleware)
  wrapper = mount(<App />)
  ;({ dispatch, getState } = wrapper
    .find("Provider[store]")
    .first()
    .props().store)

  dispatch(setGame(weaponsAndShieldsGame))
  dispatch(push("/game"))
  wrapper.update()
})

test("it shows player shield", () => {
  expect(wrapper.text()).toMatch("foo-shield")
})

test("it shows player weapon", () => {
  expect(wrapper.text()).toMatch("foo-weapon")
})
