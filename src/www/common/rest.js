const rest = async (url, options) => {
  const response = await fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json; charset=utf-8",
      ...(options && options.headers),
    },
    ...options,
  })

  const result = await response.json()

  if (!response.ok)
    return Promise.reject({
      response: response,
      data: result,
    })

  return result
}

rest.get = rest

rest.put = (url, body, options) =>
  rest(url, {
    method: "PUT",
    body: JSON.stringify(body),
    ...options,
  })

rest.post = (url, body, options) =>
  rest(url, {
    method: "POST",
    body: JSON.stringify(body),
    ...options,
  })

rest.delete = (url, options) => rest(url, { method: "DELETE", ...options })

export default rest
