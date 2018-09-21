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

test("it shows username", () => {
  expect(wrapper.text()).toMatch(fooGame.player.username)
})

test("it shows room name", () => {
  expect(wrapper.text()).toMatch(fooGame.room.name)
})

test("it shows room description", () => {
  expect(wrapper.text()).toMatch(fooGame.room.description.slice(0, 10))
})

test("offers look", () => {
  wrapper
    .find('[command="look"]')
    .find("[onClick]")
    .first()
    .simulate("click")

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "look",
  })
})
