import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import makeDucksRoot from "../../makeDucksRoot"
import { POST_COMMAND } from "../actions/postCommand"
import PostCommandButton from "../components/PostCommandButton"

describe("game duck > PostCommandButton", () => {
  let DucksRoot
  let getState
  let dispatch
  let spyMiddleware
  beforeEach(() => {
    spyMiddleware = makeSpyMiddleware()
    ;({
      DucksRoot,
      store: { getState, dispatch },
    } = makeDucksRoot(undefined, spyMiddleware))
  })

  test("shows its children", () => {
    const wrapper = mount(
      <DucksRoot>
        <PostCommandButton>my text</PostCommandButton>
      </DucksRoot>,
    )

    expect(wrapper.text()).toMatch("my text")
  })

  test("creates a postCommandAction with click", () => {
    const wrapper = mount(
      <DucksRoot>
        <PostCommandButton
          username="foo-username"
          command="foo-command"
          arguments={["arg0", "arg1"]}
        >
          my text
        </PostCommandButton>
      </DucksRoot>,
    )

    wrapper
      .find("[onClick]")
      .first()
      .simulate("click")
    const command = spyMiddleware.getAction(POST_COMMAND)

    expect(command).toMatchObject({
      username: "foo-username",
      command: "foo-command",
      arguments: ["arg0", "arg1"],
    })
  })
})
