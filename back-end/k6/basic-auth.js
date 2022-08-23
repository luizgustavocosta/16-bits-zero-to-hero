// Example from https://k6.io/docs/examples/http-authentication/
import encoding from 'k6/encoding';
import http from 'k6/http';
import { check } from 'k6';

const username = 'boba';
const password = 'fett';

export default function () {
  const credentials = `${username}:${password}`;

  const encodedCredentials = encoding.b64encode(credentials);
  const options = {
    headers: {
      Authorization: `Basic ${encodedCredentials}`,
    },
  };

  let res = http.get(`http://localhost:8080/basicauth`, options);

  check(res, {
    'status is 200': (response) => response.status === 200,
    'is authenticated': (response) => response.json().authenticated === true,
    'is correct user': (response) => response.json().user === username,
  });
}
