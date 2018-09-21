import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import { POST_COMMAND } from "../ducks/game/actions/postCommand"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

jest.useFakeTimers()

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
})

test("sends on look command after setGame is received", () => {
  dispatch(setGame(fooGame))

  jest.runAllTimers()

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "look",
  })
})

test("sends on look once command after many setGame are received", () => {
  dispatch(setGame(fooGame))
  dispatch(setGame(fooGame))

  jest.runAllTimers()

  const commands = spyMiddleware
    .getActions()
    .filter(({ type }) => type === POST_COMMAND)

  expect(commands).toHaveLength(1)
})
