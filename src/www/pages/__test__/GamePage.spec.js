import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"
import StateGameView from "../../ducks/game/components/StateGameView"
import { setGame } from "../../ducks/game/actions/setGame"

describe("pages > LoginPage", () => {
  let wrapper
  let dispatch
  let getState
  beforeEach(() => {
    const App = makeApp()
    wrapper = mount(<App />)
    ;({ dispatch, getState } = wrapper
      .find("Provider[store]")
      .first()
      .props().store)
  })

  test("without game it redirects to login", () => {
    dispatch(push(`/game`))
    wrapper.update()

    expect(getState().router).toMatchObject({
      location: { pathname: "/login" },
    })
  })

  test("has a StateGameView", () => {
    dispatch(setGame({ player: { username: "kiriko" }, room: {} }))
    dispatch(push(`/game`))
    wrapper.update()

    expect(wrapper.find(StateGameView)).toExist()
  })
})
