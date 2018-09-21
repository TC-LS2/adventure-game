import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import { POST_COMMAND } from "../ducks/game/actions/postCommand"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

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

  dispatch(setGame(fooGame))
  dispatch(push("/game"))
  wrapper.update()
})

test("it shows player current key", () => {
  expect(wrapper.text()).toMatch("foo-key")
})

test("it shows room current item", () => {
  expect(wrapper.text()).toMatch("foo-item")
})

test("it offers get the item", () => {
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

test("it offers move through closed door", () => {
  wrapper
    .find({ command: "move", arguments: ["east"] })
    .find("[onClick]")
    .first()
    .simulate("click")

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "move",
    arguments: ["east"],
  })
})
