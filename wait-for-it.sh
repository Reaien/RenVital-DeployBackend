#!/usr/bin/env bash
# wait-for-it.sh: espera a que un host y puerto estén disponibles

set -e

host="$1"
shift
port="$1"
shift

while ! nc -z "$host" "$port"; do
  echo "Esperando a que $host:$port esté disponible..."
  sleep 1
done

exec "$@"
