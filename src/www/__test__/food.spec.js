import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import { POST_COMMAND } from "../ducks/game/actions/postCommand"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

const foodGame = {
  ...fooGame,
  room: {
    ...fooGame.room,
    item: { name: "foo-food", type: "food" },
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

  dispatch(setGame(foodGame))
  dispatch(push("/game"))
  wrapper.update()
})

test("it shows room current food", () => {
  expect(wrapper.text()).toMatch("foo-food")
})

test("it offers get the food", () => {
  wrapper
    .find({ command: "get" })
    .find("[onClick]")
    .first()
    .simulate("click")

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "get",
  })
})
