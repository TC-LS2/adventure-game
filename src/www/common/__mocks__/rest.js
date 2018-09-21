import makeAsyncDeferrer from "async-deferrer"

const restMock = jest.fn()

beforeEach(() => {
  const results = []
  let nextResult = 0
  let nextResolve = 0

  restMock.mockReset()

  restMock.mockImplementation(() => {
    if (results.length <= nextResult) {
      results[nextResult] = makeAsyncDeferrer()
    }
    const result = results[nextResult]()
    nextResult += 1
    return result
  })

  restMock.resolve = data => {
    if (results.length <= nextResolve) {
      results[nextResolve] = makeAsyncDeferrer()
    }

    const promise = results[nextResolve](data)
    nextResolve += 1
    return promise
  }

  restMock.reject = rejection =>
    restMock.resolve(Promise.reject(rejection)).catch(x => x)

  restMock.delete = (url, options) =>
    restMock(url, { method: "DELETE", ...options })
  restMock.get = (url, options) => restMock(url, { method: "GET", ...options })
  restMock.post = (url, body, options) =>
    restMock(url, { method: "POST", body, ...options })
  restMock.put = (url, body, options) =>
    restMock(url, { method: "PUT", body, ...options })
})

export default restMock
