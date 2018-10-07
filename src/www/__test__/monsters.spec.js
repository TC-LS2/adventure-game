import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import { POST_COMMAND } from "../ducks/game/actions/postCommand"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

const monstersGame = {
  ...fooGame,
  room: {
    ...fooGame.room,
    monster: { name: "foo-monster" },
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

  dispatch(setGame(monstersGame))
  dispatch(push("/game"))
  wrapper.update()
})

test("it shows room monster", () => {
  expect(wrapper.text()).toMatch("foo-monster")
})

test("it offers attack the monster", () => {
  wrapper
    .find({ command: "attack" })
    .find("[onClick]")
    .first()
    .simulate("click")

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "attack",
  })
})
