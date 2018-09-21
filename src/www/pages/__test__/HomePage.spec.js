import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"
import HomePage from "../HomePage"

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

  test("starts at Home Page", () => {
    expect(wrapper.find(HomePage)).toExist()
  })

  test("it offers to go to play game", () => {
    expect(wrapper.find('[to="/game"]')).toExist()
  })
})
