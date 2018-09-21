import { mount } from "enzyme"
import React from "react"

import makeDucksRoot from "../../makeDucksRoot"
import { setAlert } from "../actions/setAlert"
import StateAlert from "../components/StateAlert"
import getAlert from "../selectors/getAlert"

describe("alert duck > StateAlert", () => {
  let DucksRoot
  let getState
  let dispatch
  beforeEach(() => {
    ;({
      DucksRoot,
      store: { getState, dispatch },
    } = makeDucksRoot())
  })

  test("by default alert shows nothing", () => {
    const wrapper = mount(
      <DucksRoot>
        <StateAlert />
      </DucksRoot>,
    )

    expect(wrapper.text()).toBe("")
  })

  test("setAlert shows the alert text", () => {
    const wrapper = mount(
      <DucksRoot>
        <StateAlert />
      </DucksRoot>,
    )

    dispatch(setAlert("foo-alert"))

    expect(wrapper.text()).toMatch("foo-alert")
  })

  test("click on dissmiss removes the alert text", () => {
    const wrapper = mount(
      <DucksRoot>
        <StateAlert />
      </DucksRoot>,
    )
    dispatch(setAlert("foo-alert"))
    wrapper.update()

    wrapper.find("[onClick]").simulate("click")

    expect(getAlert(getState())).toBeNull()
  })
})
