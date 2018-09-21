import makeSpyMiddleware from "spy-middleware"

jest.mock("../../../common/rest")
import restMock from "../../../common/rest"

import makeDucksRoot from "../../makeDucksRoot"
import { SET_ALERT } from "../../alert/actions/setAlert"
import getAlert from "../../alert/selectors/getAlert"
import { putWorld } from "../actions/putWorld"
import { setWorld } from "../actions/setWorld"
import getWorld from "../selectors/getWorld"

const fooWorld = {
  rooms: "foo-rooms",
  monsters: "foo-monsters",
  items: "foo-items",
}

describe("worldBuilder duck", () => {
  let getState
  let dispatch
  let spyMiddleware
  beforeEach(() => {
    spyMiddleware = makeSpyMiddleware()
    ;({
      store: { getState, dispatch },
    } = makeDucksRoot(undefined, spyMiddleware))
  })

  test("setWorld updates the current state", () => {
    dispatch(setWorld(fooWorld))

    expect(getWorld(getState())).toEqual(fooWorld)
  })

  test("putWorld success adds an alert", async () => {
    dispatch(putWorld(fooWorld))
    restMock.resolve({})

    await spyMiddleware.until(SET_ALERT)
    expect(getAlert(getState())).toMatch(/succe/i)
  })

  test("putWorld failure adds an alert", async () => {
    dispatch(putWorld(fooWorld))
    restMock.reject()

    await spyMiddleware.until(SET_ALERT)
    expect(getAlert(getState())).toMatch(/fail/i)
  })

  test("data is stored under state.worldBuilder", () => {
    dispatch(setWorld(fooWorld))

    const worldBuilderJson = JSON.stringify(getState().worldBuilder)
    expect(worldBuilderJson).toMatch(fooWorld.rooms)
    expect(worldBuilderJson).toMatch(fooWorld.monsters)
    expect(worldBuilderJson).toMatch(fooWorld.items)
  })
})
