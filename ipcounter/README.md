# Unique Ip counter

Chosen approach:

* Because every part of IP address can be only [0..255] which is fit into 1 byte we can
  compress ip to integer(4 bytes)
* Next I use two Bitsets (for positive and negative ips) to check whether ip was already meet

Implementation requires ~600mb of the heap to run.

P.S. there is assumption made that all ips are valid in the file

## Running

One-liner is provided to build and run cli app:

```bash
cd ipcounter
./run-ip-ipcounter.sh /path/to/up_addresses
```

It tooks ~10 mins to complete on MacBook

## Results

Unique IPs = 1000000000, Total IPs = 8000000000
for test file

