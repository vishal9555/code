name: Publish VideoFetch

on:
  push:
    branches:
      - main

jobs:
  publish:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up Node.js
        uses: actions/setup-node@v2
        with:
          node-version: '16'

      - name: Build Plugin
        run: |
          dist="$(pwd)/VideoFetch.fda"
          cd plugin/yt_dlp/
          rm -f "$dist"
          zip -r "$dist" *
      - name: Create Release
        id: create_release
        uses: actions/create-release@v1
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        with:
          tag_name: v${{ github.run_number }}
          release_name: Release v${{ github.run_number }}
          draft: false
          prerelease: false
        continue-on-error: true

      - name: Get Release ID
        id: get_release_id
        run: echo "::set-output name=release_id::${{ steps.create_release.outputs.release_id }}"

      - name: Upload Plugin
        id: upload_plugin
        uses: actions/upload-release-asset@v1
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        with:
          upload_url: ${{ steps.create_release.outputs.upload_url }}
          asset_path: ./VideoFetch.fda
          asset_name: VideoFetch.fda
          asset_content_type: application/zip

      - name: Cleanup
        run: |
          rm -f ./VideoFetch.fda
      - name: Set Upload URL and Release ID as Outputs
        run: |
          echo "::set-output name=upload_url::${{ steps.create_release.outputs.upload_url }}"
          echo "::set-output name=release_id::${{ steps.get_release_id.outputs.release_id }}"