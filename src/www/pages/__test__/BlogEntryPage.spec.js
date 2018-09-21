import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"
import blog from "../../blog"

describe("pages > BlogEntryPage", () => {
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

  blog.forEach(entry => {
    test(`shows entry ${entry.id}`, () => {
      dispatch(push(`/blog/${entry.id}`))

      expect(wrapper.text()).toMatch(entry.title)
      expect(wrapper.text()).toMatch(entry.tagline)
    })
  })
})
