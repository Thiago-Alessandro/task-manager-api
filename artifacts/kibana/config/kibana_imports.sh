#!/bin/bash

KIBANA_URL="https://kibana:5601"
FILE_PATH="/usr/local/bin/saved_objects/export.ndjson"

# Importa os objetos salvos para o Kibana
curl -u "elastic:changeme" -X POST "${KIBANA_URL}/api/saved_objects/_import?overwrite=true" \
-H "kbn-xsrf: true" \
--form file=@${FILE_PATH} --cacert /certs/ca/ca.crt

echo "Importação concluída!"