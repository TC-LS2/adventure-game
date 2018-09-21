jest.mock("../../../common/rest")
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import makeDucksRoot from "../../makeDucksRoot"
import StateWorldBuilder from "../components/StateWorldEditor"
import { setWorld } from "../actions/setWorld"
import getWorld from "../selectors/getWorld"
import { PUT_WORLD } from "../actions/putWorld"

const fooWorld = {
  rooms: "foo-rooms",
  monsters: "foo-monsters",
  items: "foo-items",
}

describe("worldBuilder duck > StateWorldEditor", () => {
  let DucksRoot
  let getState
  let dispatch
  let mountStateWorldBuilder
  let spyMiddleware
  beforeEach(() => {
    spyMiddleware = makeSpyMiddleware()
    ;({
      DucksRoot,
      store: { getState, dispatch },
    } = makeDucksRoot(undefined, spyMiddleware))

    mountStateWorldBuilder = () =>
      mount(
        <DucksRoot>
          <StateWorldBuilder />
        </DucksRoot>,
      )
  })

  test("setWorld updates the current world", () => {
    const wrapper = mountStateWorldBuilder()

    dispatch(setWorld(fooWorld))
    expect(wrapper.text()).toMatch(fooWorld.rooms)
    expect(wrapper.text()).toMatch(fooWorld.monsters)
    expect(wrapper.text()).toMatch(fooWorld.items)
  })

  test("changes in editors are stored", () => {
    dispatch(setWorld(fooWorld))
    const wrapper = mountStateWorldBuilder()
    const roomsEditor = wrapper.find({ value: fooWorld.rooms }).find("textarea")
    const monstersEditor = wrapper
      .find({ value: fooWorld.monsters })
      .find("textarea")
    const itemsEditor = wrapper.find({ value: fooWorld.items }).find("textarea")

    roomsEditor.simulate("change", { target: { value: "new-rooms" } })
    monstersEditor.simulate("change", { target: { value: "new-monsters" } })
    itemsEditor.simulate("change", { target: { value: "new-items" } })

    expect(getWorld(getState())).toMatchObject({
      rooms: "new-rooms",
      monsters: "new-monsters",
      items: "new-items",
    })
  })

  test("click on save launches a putWorld", () => {
    dispatch(setWorld(fooWorld))
    const wrapper = mountStateWorldBuilder()

    wrapper.find("span[onClick]").simulate("click")
    const { world } = spyMiddleware.getAction(PUT_WORLD)

    expect(world).toEqual(fooWorld)
  })
})
