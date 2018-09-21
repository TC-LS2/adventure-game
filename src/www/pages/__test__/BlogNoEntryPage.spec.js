import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"

import makeApp from "../../makeApp"

describe("pages > BlogNoEntryPage", () => {
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

  test("no valid entry redirects to blog", () => {
    dispatch(push(`/blog/undefined`))

    expect(getState().router).toMatchObject({ location: { pathname: "/blog" } })
  })
})
