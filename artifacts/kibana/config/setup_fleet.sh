CERTIFICATE=$(awk '/-----BEGIN CERTIFICATE-----/{flag=1} flag; /-----END CERTIFICATE-----/{flag=0}' $FLEET_SERVER_ELASTICSEARCH_CA | awk '{printf "%s\\n", $0} END {print ""}' | sed 's/\\n$//')

FINGERPRINT=$(openssl x509 -in $FLEET_SERVER_ELASTICSEARCH_CA -fingerprint -sha256 -noout | awk -F= '{print $2}' | sed 's/://g')

curl -X PUT "https://elastic:changeme@kibana:5601/api/fleet/outputs/fleet-default-output" \
-H "kbn-xsrf: true" \
-H "Content-Type: application/json" \
-d '{
 "name": "default",
 "type": "elasticsearch",
 "hosts": ["https://es01:9200"],
 "ca_trusted_fingerprint": "'"$FINGERPRINT"'",
  "ssl": {
    "certificate_authorities": ["'"$CERTIFICATE"'"]
  }
}' --cacert /certs/ca/ca.crt

echo "Fleet output setup concluded successfully!"