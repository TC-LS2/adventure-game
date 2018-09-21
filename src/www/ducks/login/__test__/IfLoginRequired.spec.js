import { mount } from "enzyme"
import React from "react"

import makeDucksRoot from "../../makeDucksRoot"
import { setGame } from "../../game/actions/setGame"
import IfLoginRequired from "../components/IfLoginRequired"

describe("login duck > IfLoginRequired", () => {
  let DucksRoot
  let dispatch
  beforeEach(() => {
    ;({
      DucksRoot,
      store: { dispatch },
    } = makeDucksRoot())
  })

  test("shows children if no login information", () => {
    const wrapper = mount(
      <DucksRoot>
        <IfLoginRequired>do login please</IfLoginRequired>
      </DucksRoot>,
    )

    expect(wrapper.text()).toMatch("do login please")
  })

  test("shows nothing if there was a login", () => {
    dispatch(setGame({ player: { username: "yes" } }))
    const wrapper = mount(
      <DucksRoot>
        <IfLoginRequired>do login please</IfLoginRequired>
      </DucksRoot>,
    )

    expect(wrapper.text()).toBe("")
  })
})
