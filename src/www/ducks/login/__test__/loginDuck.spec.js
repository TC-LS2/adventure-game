import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

jest.mock("../../../common/rest")
import makeDucksRoot from "../../makeDucksRoot"
import { setUsername } from "../actions/setUsername"
import getUsername from "../selectors/getUsername"
import { POST_COMMAND } from "../../game/actions/postCommand"
import { login } from "../actions/login"
import { setGame } from "../../game/actions/setGame"
import { push } from "connected-react-router"

describe("login duck", () => {
  let getState
  let dispatch
  let spyMiddleware
  let DucksRoot
  beforeEach(() => {
    spyMiddleware = makeSpyMiddleware()
    ;({
      DucksRoot,
      store: { getState, dispatch },
    } = makeDucksRoot(undefined, spyMiddleware))

    // required because a *bug* of connected-react-router
    mount(
      <DucksRoot>
        <div />
      </DucksRoot>,
    )
  })

  test("setUsername updates the state", () => {
    dispatch(setUsername("kirito"))

    expect(getUsername(getState())).toBe("kirito")
  })

  test("login fires a look command", () => {
    dispatch(login("kirito"))

    const command = spyMiddleware.getAction(POST_COMMAND)
    expect(command).toMatchObject({
      username: "kirito",
      command: "look",
    })
  })

  test("setGame from login page changes to game page", () => {
    dispatch(push("/login"))
    dispatch(setGame({ player: { username: "yes" } }))

    expect(getState()).toMatchObject({
      router: { location: { pathname: "/game" } },
    })
  })

  test("data is stored under state.login", () => {
    dispatch(setUsername("kirito"))

    const loginJson = JSON.stringify(getState().login)
    expect(loginJson).toMatch("kirito")
  })
})
