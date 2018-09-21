import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"
import StateWorldEditor from "../../ducks/world-builder/components/StateWorldEditor"

describe("pages > BuilderPage", () => {
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

  test("has a StateWorldEditor", () => {
    dispatch(push(`/builder`))
    wrapper.update()

    expect(wrapper.find(StateWorldEditor)).toExist()
  })
})
