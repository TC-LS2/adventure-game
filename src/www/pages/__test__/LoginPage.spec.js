import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"
import StateLoginForm from "../../ducks/login/components/StateLoginForm"

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

  test("has a StateLoginForm", () => {
    dispatch(push(`/login`))
    wrapper.update()

    expect(wrapper.find(StateLoginForm)).toExist()
  })
})
