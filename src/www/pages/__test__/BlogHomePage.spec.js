import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"
import blog from "../../blog"

describe("pages > BlogHomePage", () => {
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

  test(`lists entries`, () => {
    dispatch(push(`/blog`))

    blog.forEach(entry => {
      expect(wrapper.text()).toMatch(entry.title)
    })
  })
})
