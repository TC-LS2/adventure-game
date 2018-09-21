import makeDucksRoot from "../../makeDucksRoot"
import { setAlert } from "../actions/setAlert"
import getAlert from "../selectors/getAlert"

describe("alert duck", () => {
  let getState
  let dispatch
  beforeEach(() => {
    ;({
      store: { getState, dispatch },
    } = makeDucksRoot())
  })

  test("by default alert is null", () => {
    expect(getAlert(getState())).toBeNull()
  })

  test("setAlert updates state alert", () => {
    dispatch(setAlert("foo-alert"))

    expect(getAlert(getState())).toMatch("foo-alert")
  })

  test("alert text is stored under state.alert", () => {
    dispatch(setAlert("foo-alert"))
    const alertJson = JSON.stringify(getState().alert)

    expect(alertJson).toMatch("foo-alert")
  })
})
