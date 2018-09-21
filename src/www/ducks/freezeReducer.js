const freezeds = new WeakSet()

const freezeState = state => {
  if (state && typeof state === "object" && !freezeds.has(state)) {
    freezeds.add(state)
    Object.keys(state).forEach(k => {
      const val = state[k]
      if (typeof val === "object") {
        state[k] = freezeState(val)
      }
    })
  }
  Object.freeze(state)
  return state
}

const freezeReducer = reducer => (state, action) =>
  freezeState(reducer(state, action))

export default freezeReducer
