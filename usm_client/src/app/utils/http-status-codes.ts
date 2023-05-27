export const httpStatusCodes = {
  ok: {
    code: 200,
    message: 'Success.',
  },
  created: {
    code: 201,
    message:
      'The request succeeded, and a new resource was created as a result.',
  },
  badRequest: {
    code: 400,
    message:
      'The server cannot or will not process the request due to something that is perceived to be a client error.',
  },
  unauthorized: {
    code: 401,
    message:
      'The client must authenticate itself to get the requested response.',
  },
  forbidden: {
    code: 403,
    message: 'The client does not have access rights to the content.',
  },
  notFound: {
    code: 404,
    message: 'The server can not find the requested resource.',
  },
  methodNotAllowed: {
    code: 405,
    message:
      'The request method is known by the server but is not supported by the target resource.',
  },
  internalServerError: {
    code: 500,
    message:
      'The server has encountered a situation it does not know how to handle.',
  },
  badGateway: {
    code: 502,
    message:
      'The server, while working as a gateway to get a response needed to handle the request, got an invalid response.',
  },
  gatewayTimeout: {
    code: 504,
    message: 'Cannot get a response in time.',
  },
};
