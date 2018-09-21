import makeSpyMiddleware from "spy-middleware"

jest.mock("../../../common/rest")
import restMock from "../../../common/rest"

import fooGame from "../../../__test__/fooGame"
import makeDucksRoot from "../../makeDucksRoot"
import { SET_ALERT } from "../../alert/actions/setAlert"
import getAlert from "../../alert/selectors/getAlert"
import { postCommand } from "../actions/postCommand"
import { setGame, SET_GAME } from "../actions/setGame"
import getGame from "../selectors/getGame"
import hasGame from "../selectors/hasGame"

describe("game duck", () => {
  let getState
  let dispatch
  let spyMiddleware
  beforeEach(() => {
    spyMiddleware = makeSpyMiddleware()
    ;({
      store: { getState, dispatch },
    } = makeDucksRoot(undefined, spyMiddleware))
  })

  test("setGame stores a game", () => {
    dispatch(setGame(fooGame))

    expect(getGame(getState())).toEqual(fooGame)
  })

  test("hasGame returns false if there is no game", () => {
    expect(hasGame(getState())).toBe(false)
  })

  test("hasGame returns true if there is a game", () => {
    dispatch(setGame(fooGame))

    expect(hasGame(getState())).toBe(true)
  })

  test("postCommand sends username, command, and arguments", () => {
    dispatch(
      postCommand("new-username", "new-command", ["new-arg0", "new-arg1"]),
    )

    expect(restMock).toHaveBeenCalledWith("/api/v1/commands", {
      method: "POST",
      body: {
        username: "new-username",
        command: "new-command",
        arguments: ["new-arg0", "new-arg1"],
      },
    })
  })

  test("postCommand server response is set to game", async () => {
    dispatch(postCommand())
    restMock.resolve(fooGame)

    await spyMiddleware.until(SET_GAME)
    expect(getGame(getState())).toEqual(fooGame)
  })

  test("postCommand failed response is sent to alert", async () => {
    dispatch(postCommand())
    restMock.reject({ data: { message: "foo-failed" } })

    await spyMiddleware.until(SET_ALERT)
    expect(getAlert(getState())).toBe("foo-failed")
  })

  test("data is stored under state.game", () => {
    dispatch(setGame(fooGame))

    const gameJson = JSON.stringify(getState().game)
    expect(gameJson).toMatch(fooGame.player.username)
  })
})
